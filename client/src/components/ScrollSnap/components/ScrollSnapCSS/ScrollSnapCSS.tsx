import type { UIEventHandler } from 'react';
import { useLayoutEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import type { ScrollSnapProps } from '../../types';
import ScrollSnapCSSItemList from './ScrollSnapCSSItemList';

// CSS scroll-snap의 scrollPosition과 parent에서 내려준 scrollPosition의 불일치를
// 어느정도 허용할 것인지 설정
// 값이 0일 경우 오차를 허용하지 않음
const SCROLL_UNMATCH_TOLERANCE = 0.1;

// CSS scroll-snap에서 나타나는 glitch를 고치기 위한 상수
// 스크롤이 너무 많이 바뀌었을 경우 캔슬한다
const SCROLL_SNAP_GLITCH_RANGE = 1;

const ScrollSnapCSS = <Item,>(props: ScrollSnapProps<Item>) => {
  const {
    activeIndex,
    onActiveIndexChange,
    scrollPosition: parentScrollPosition,
    onScrollPositionChange,
    items,
    itemRenderer,
    enableRolling,
    timingFn,
    ...divProps
  } = props;
  const [clientScrollPosition, setClientScrollPosition] = useState(parentScrollPosition);
  const [shouldSync, setShouldSync] = useState(true);

  // 마지막 아이템에서 첫 번째 아이템으로 넘어가려고 할 때 허용할 지, 허용하지 않을 지
  // 여부를 결정하는 함수
  const clampPosition = (position: number) => Math.max(0, Math.min(position, items.length - 1));

  const setActiveIndex = (activePosition: number) => {
    onActiveIndexChange(clampPosition(activePosition));
  };

  const containerRef = useRef<HTMLDivElement>(null);

  const getDomScrollPosition = () => {
    const $element = containerRef.current;
    if (!($element instanceof HTMLDivElement)) {
      return clientScrollPosition;
    }
    return $element.scrollTop / $element.clientHeight;
  };

  const handleScroll: UIEventHandler = (event) => {
    const domScrollPosition = getDomScrollPosition();
    const refinedPosition =
      Math.abs(domScrollPosition - Math.round(domScrollPosition)) < 0.01
        ? Math.round(domScrollPosition)
        : domScrollPosition;

    // A glitch has been detected. However, fixing this may actually impair the user experience,
    // so it has been commented out.
    //
    // const seemsGlitch = Math.abs(clientScrollPosition - refinedPosition) > SCROLL_SNAP_GLITCH_RANGE;
    // if (seemsGlitch) {
    //   setShouldSync(true);
    //   return;
    // }
    setActiveIndex(Math.round(refinedPosition));
    onScrollPositionChange(clampPosition(refinedPosition));

    setClientScrollPosition(refinedPosition);
    setShouldSync(false);
  };

  useLayoutEffect(() => {
    const $element = containerRef.current;
    if (!($element instanceof HTMLDivElement)) return;

    if (shouldSync) {
      $element.scrollTo({ top: $element.clientHeight * clientScrollPosition });
      setShouldSync(false);
    }
  }, [clientScrollPosition, shouldSync]);

  // 부모에서 내려준 scroll position이 DOM의 scroll position과
  // 불일치가 심한지 여부
  const shouldSyncWithParent = () => {
    const domScrollPosition = getDomScrollPosition();
    return Math.abs(domScrollPosition - parentScrollPosition) > SCROLL_UNMATCH_TOLERANCE;
  };

  // DOM scroll position과 parent에서 내려준 scroll position이 mismatch할 때
  // scroll을 강제로 이동시킬 필요가 있음
  if (shouldSyncWithParent() && !shouldSync) {
    setClientScrollPosition(parentScrollPosition);
    setShouldSync(true);
  }

  return (
    <Container {...divProps} ref={containerRef} onScroll={handleScroll}>
      <ScrollSnapCSSItemList scrollPosition={clientScrollPosition} items={items} itemRenderer={itemRenderer} />
    </Container>
  );
};

export default ScrollSnapCSS;

const Container = styled.div`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  width: 100%;
  height: 100%;

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;
