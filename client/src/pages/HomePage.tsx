import { useEffect, useState } from 'react';
import { ScrollSnap, ScrollSnapProvider, easeOutExpo } from 'yozm-cafe-react-scroll-snap';
import CafeCard from '../components/CafeCard';
import useCafes from '../hooks/useCafes';
import type { Cafe } from '../types';
import { withGAEvent } from '../utils/GoogleAnalytics';

const PREFETCH_OFFSET = 2;

const HomePage = () => {
  const { cafes, fetchNextPage, isFetching, hasNextPage } = useCafes();

  const [scrollPosition, setScrollPosition] = useState(0);
  const [activeIndex, setActiveIndex] = useState(0);

  const shouldFetch =
    hasNextPage &&
    !isFetching &&
    cafes.findIndex((cafe) => cafe.id === cafes[activeIndex].id) + PREFETCH_OFFSET >= cafes.length;

  if (shouldFetch) {
    fetchNextPage();
  }

  const itemRenderer = (cafe: Cafe) => <CafeCard key={cafe.id} cafe={cafe} />;

  useEffect(withGAEvent('cafe_view', { cafeName: cafes[activeIndex].name }), [activeIndex]);

  return (
    <ScrollSnapProvider
      scrollPosition={scrollPosition}
      onScrollPositionChange={setScrollPosition}
      activeIndex={activeIndex}
      onActiveIndexChange={setActiveIndex}
      items={cafes}
      easingFunction={easeOutExpo}
      itemRenderer={itemRenderer}
    >
      <ScrollSnap style={{ height: '100%' }} />
    </ScrollSnapProvider>
  );
};

export default HomePage;
