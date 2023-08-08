import type { RefObject } from 'react';
import { useEffect, useState } from 'react';

/**
 * 주어진 ref의 HTMLElement가 화면 상에
 * 교차되는지 여부를 observe하고 그 결과 값을 반환하는 훅입니다.
 *
 * @example
 * const Example = () => {
 *   const ref = useRef<HTMLDivElement>(null);
 *   const intersection = useIntersection(ref, { threshold: 1 });
 *
 *   return (
 *     <div ref={ref}>
 *       {intersection?.intersectionRatio === 1
 *         ? '전체가 보이는 중'
 *         : '잘 안보이는 중'}
 *     </div>
 *   );
 * }
 */
const useIntersection = (ref: RefObject<HTMLElement>, options: IntersectionObserverInit = {}) => {
  const [entry, setEntry] = useState<IntersectionObserverEntry | null>(null);

  useEffect(() => {
    if (ref.current) {
      const handler = (entries: IntersectionObserverEntry[]) => {
        setEntry(entries[0]);
      };

      const observer = new IntersectionObserver(handler, options);
      observer.observe(ref.current);

      return () => {
        setEntry(null);
        observer.disconnect();
      };
    }
  }, [ref.current, options.threshold, options.root, options.rootMargin]);

  return entry;
};

export default useIntersection;
