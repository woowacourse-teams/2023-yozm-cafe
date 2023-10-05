import type { EventHandler, SyntheticEvent } from 'react';
import { useCallback, useMemo } from 'react';

/**
 * ScrollSnapContainer의 하위 요소에서 열린 모달에서 스크롤 혹은 스와이프를 할 시
 * ScrollSnapContainer에 전파되어 스와이프가 동작하는 문제가 발생합니다.
 * ScrollSnapContainer의 스와이프가 동작하지 않도록 갖가지 이벤트의 propagation을 막아주는 훅입니다.
 */
const useScrollSnapGuard = () => {
  const preventPropagation: EventHandler<SyntheticEvent> = useCallback((event) => {
    event.stopPropagation();
  }, []);

  const handlers = useMemo(
    () => ({
      onTouchStart: preventPropagation,
      onTouchMove: preventPropagation,
      onTouchEnd: preventPropagation,
      onMouseDown: preventPropagation,
      onMouseMove: preventPropagation,
      onMouseUp: preventPropagation,
      onMouseLeave: preventPropagation,
      onWheel: preventPropagation,
    }),
    [preventPropagation],
  );

  return handlers;
};

export default useScrollSnapGuard;
