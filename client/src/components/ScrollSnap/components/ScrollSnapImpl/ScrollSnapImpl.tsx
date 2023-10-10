import type { MouseEventHandler, TouchEventHandler } from 'react';
import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import useEffectEvent from '../../../../shims/useEffectEvent';
import { easeOutExpo } from '../../../../utils/timingFunctions';
import type { ScrollSnapProps } from '../../types';
import ScrollSnapImplItemList from './ScrollSnapImplItemList';

// 터치 스와이프가 수직 혹은 수평 방향인지 판단할 때 샘플링하는 거리
const SWIPE_TOUCH_DECISION_RADIUS = 4;

// 스와이프 시 다음 아이템으로 이동하는 시간(ms)
const SWIPE_SNAP_TRANSITION_DURATION = 300;

// 빠르게 휙 스와이프 시:
//   스와이프 판정을 위해 직전 {value}ms 터치 포인트와 비교
const SWIPE_FAST_SCROLL_TIME_DURATION = 100;

// 빠르게 휙 스와이프 시:
//   직전 터치 포인트와 비교하여 {value}% 만큼 이동하였다면 스와이프 처리
//   작을 수록 짧게 스와이프해도 판정됨
//   클 수록 넓게 스와이프해야 판정됨
const SWIPE_FAST_SCROLL_DISTANCE_RATIO = 0.03;

// 음수 mod 시 양수 값을 얻기 위한 함수
const mod = (n: number, m: number) => ((n % m) + m) % m;

type BoxModel = {
  pageY: number;
  height: number;
};

type TouchPoint = {
  x: number;
  y: number;
};

type MachineState =
  // 아무런 동작도 하지 않는 상태
  | {
      label: 'idle';
    }
  // 터치 방향(좌우 혹은 상하)에 따라 스와이프를 해야할 지 결정하는 상태
  // 이 상태에선 실질적으로 스와이프가 동작하지 않는다
  | {
      label: 'touchdecision';
      originTouchPoint: TouchPoint;
    }
  // 터치의 움직임에 따라 스크롤이 동작하는 상태
  | {
      label: 'touchmove';
      prevPosition: number;
      recentPositionHistory: { timestamp: number; position: number }[];
    }
  // 터치가 끝났고 스크롤이 가장 가까운 스냅 포인트로 이동하는(애니메이션) 상태
  | {
      label: 'snap';
      startedAt: number;
      startedPosition: number;
    };

const ScrollSnapImpl = <Item,>(props: ScrollSnapProps<Item>) => {
  const {
    activeIndex,
    onActiveIndexChange,
    scrollPosition,
    onScrollPositionChange,
    items,
    itemRenderer,
    enableRolling,
    timingFn = easeOutExpo,
    ...divProps
  } = props;

  const [, setNothing] = useState({});
  const rerender = () => setNothing({});

  const [boxModel, setBoxModel] = useState<BoxModel | null>(null);

  // 마지막 아이템에서 첫 번째 아이템으로 넘어가려고 할 때 허용할 지, 허용하지 않을 지
  // 여부를 결정하는 함수
  const clampPosition = enableRolling
    ? (position: number) => mod(position, items.length)
    : (position: number) => Math.max(0, Math.min(position, items.length - 1));

  const setScrollPosition = (scrollPosition: number) => {
    onScrollPositionChange(clampPosition(scrollPosition));
  };

  const setActiveIndex = (activePosition: number) => {
    onActiveIndexChange(clampPosition(activePosition));
  };

  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const $container = containerRef.current;
    if ($container === null) return;

    const observer = new ResizeObserver((entries) => {
      const entry = entries.pop();
      if (!entry) return;

      const { y: pageY, height } = entry.contentRect;
      setBoxModel({ pageY, height });
    });
    observer.observe($container);

    return () => observer.disconnect();
  }, []);

  const [machineState, setMachineState] = useState<MachineState>({
    label: 'idle',
  });
  const transitionMachineState = (state: MachineState) => {
    setMachineState(state);
  };

  // returns normalized by container box height.
  // if touch is inside of screen, then 0.0 ~ 1.0
  // or outside, it can be negative or greater than 1.0
  const normalizePageY = (pageY: number): number => {
    if (!boxModel) return 0;

    return (pageY - boxModel.pageY) / boxModel.height;
  };

  const onTouchStart = (touchPoint: TouchPoint) => {
    transitionMachineState({
      label: 'touchdecision',
      originTouchPoint: touchPoint,
    });
  };

  const onTouchDecision = (touchPoint: TouchPoint) => {
    if (machineState.label !== 'touchdecision') return;

    // swipe decision
    const { originTouchPoint } = machineState;
    const [rx, ry] = [originTouchPoint.x - touchPoint.x, originTouchPoint.y - touchPoint.y].map(Math.abs);
    if (Math.abs(rx) < SWIPE_TOUCH_DECISION_RADIUS || Math.abs(ry) < SWIPE_TOUCH_DECISION_RADIUS) {
      return;
    }

    // not my direction
    if (ry < rx) {
      transitionMachineState({ label: 'idle' });
      return;
    }

    const position = normalizePageY(touchPoint.y);
    // transition state to 'touchmove'
    transitionMachineState({
      label: 'touchmove',
      prevPosition: position,
      recentPositionHistory: [{ timestamp: Date.now(), position }],
    });
  };

  const onTouchMove = (touchPoint: TouchPoint) => {
    if (machineState.label === 'touchdecision') {
      onTouchDecision(touchPoint);
      return;
    }

    if (machineState.label !== 'touchmove') return;

    const { prevPosition, recentPositionHistory } = machineState;
    const position = normalizePageY(touchPoint.y);

    // process swipe movement
    const delta = prevPosition - position;
    setScrollPosition(scrollPosition + delta);

    // transition itself
    transitionMachineState({
      label: 'touchmove',
      prevPosition: position,
      recentPositionHistory: [...recentPositionHistory, { timestamp: Date.now(), position }].filter(
        ({ timestamp }) => Date.now() - timestamp < SWIPE_FAST_SCROLL_TIME_DURATION,
      ),
    });
  };

  const onTouchEnd = () => {
    onSnapStart();

    if (machineState.label !== 'touchmove') return;

    const { prevPosition, recentPositionHistory } = machineState;

    const recentPosition = recentPositionHistory[0]?.position;
    if (!recentPosition) return;

    const accelDelta = recentPosition - prevPosition;

    if (Math.abs(accelDelta) > SWIPE_FAST_SCROLL_DISTANCE_RATIO) {
      const positionOffset = accelDelta > 0 ? 1 : -1;
      setActiveIndex(activeIndex + positionOffset);
      return;
    }
    setActiveIndex(Math.round(scrollPosition));
  };

  const onSnapStart = () => {
    if (machineState.label === 'snap') return;

    // transition state to 'snap'
    transitionMachineState({
      label: 'snap',
      startedAt: Date.now(),
      startedPosition: scrollPosition,
    });
    onSnap();
  };

  const onSnap = () => {
    if (machineState.label !== 'snap') return;

    // transition 'snap' itself
    const { startedAt, startedPosition } = machineState;
    const x = (Date.now() - startedAt) / SWIPE_SNAP_TRANSITION_DURATION;
    if (x > 1) {
      // transition to 'idle'
      setScrollPosition(activeIndex);
      transitionMachineState({ label: 'idle' });
      return;
    }

    // 정방향 거리와 역방향 거리를 비교하여 가까운 거리로 transition
    const candidateDistance1 = activeIndex - startedPosition;
    const candidateDistance2 = (items.length - Math.abs(candidateDistance1)) * (candidateDistance1 < 0 ? 1 : -1);

    const transitionDistance =
      Math.abs(candidateDistance1) < Math.abs(candidateDistance2) ? candidateDistance1 : candidateDistance2;

    const processTransition = () => {
      // scrollTo(startedPosition + timingFn(x) * transitionDistance);
      setScrollPosition(startedPosition + timingFn(x) * transitionDistance);
      rerender(); // force trigger re-render
    };
    requestAnimationFrame(processTransition);
  };

  if (machineState.label === 'snap') onSnap();

  const handleTouchStart: TouchEventHandler = (event) => {
    const touch = event.touches[0];
    if (!touch) return;
    event.stopPropagation();

    onTouchStart({ x: touch.pageX, y: touch.pageY });
  };

  const handleTouchMove: TouchEventHandler = (event) => {
    const touch = event.touches[0];
    if (!touch) return;
    event.stopPropagation();

    onTouchMove({ x: touch.pageX, y: touch.pageY });
  };

  const handleTouchEnd: TouchEventHandler = (event) => {
    event.stopPropagation();
    onTouchEnd();
  };

  const handleMouseDown: MouseEventHandler = (event) => {
    event.stopPropagation();
    onTouchStart({ x: event.pageX, y: event.pageY });
  };

  const handleMouseMove: MouseEventHandler = (event) => {
    event.stopPropagation();
    onTouchMove({ x: event.pageX, y: event.pageY });
  };

  const handleMouseUp: MouseEventHandler = (event) => {
    event.stopPropagation();
    onTouchEnd();
  };

  const handleMouseLeave: MouseEventHandler = (event) => {
    event.stopPropagation();
    onTouchEnd();
  };

  const handleKeyDown = useEffectEvent((event: KeyboardEvent) => {
    switch (event.key) {
      case 'ArrowUp':
        setActiveIndex(activeIndex - 1);
        onSnapStart();
        break;
      case 'ArrowDown':
        setActiveIndex(activeIndex + 1);
        onSnapStart();
        break;
    }
  });

  useEffect(() => {
    window.addEventListener('keydown', handleKeyDown);

    return () => window.removeEventListener('keydown', handleKeyDown);
  }, []);

  return (
    <Container
      {...divProps}
      ref={containerRef}
      onTouchStart={handleTouchStart}
      onTouchMove={handleTouchMove}
      onTouchEnd={handleTouchEnd}
      onMouseDown={handleMouseDown}
      onMouseMove={handleMouseMove}
      onMouseUp={handleMouseUp}
      onMouseLeave={handleMouseLeave}
      // Disable wheel
      // see https://github.com/woowacourse-teams/2023-yozm-cafe/issues/480)
      // onWheel={handleWheel}
    >
      <ScrollSnapImplItemList
        scrollPosition={scrollPosition}
        items={items}
        itemRenderer={itemRenderer}
        enableRolling={enableRolling}
      />
    </Container>
  );
};

const Container = styled.div`
  cursor: grab;
  user-select: none;
  position: relative;
  overflow-y: hidden;
`;

export default ScrollSnapImpl;
