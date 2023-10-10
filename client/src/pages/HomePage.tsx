import { useState } from 'react';
import CafeCard from '../components/CafeCard';
import ScrollSnap from '../components/ScrollSnap';
import useCafes from '../hooks/useCafes';
import type { Cafe } from '../types';
import { easeOutExpo } from '../utils/timingFunctions';

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

  return (
    <ScrollSnap
      style={{ height: '100%' }}
      scrollPosition={scrollPosition}
      onScrollPositionChange={setScrollPosition}
      activeIndex={activeIndex}
      onActiveIndexChange={setActiveIndex}
      items={cafes}
      timingFn={easeOutExpo}
      itemRenderer={itemRenderer}
    />
  );
};

export default HomePage;
