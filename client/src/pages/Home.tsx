import { useState } from 'react';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useCafeActions from '../hooks/useCafeActions';
import useCafes from '../hooks/useCafes';
import useUser from '../hooks/useUser';

const PREFETCH_OFFSET = 2;

const Home = () => {
  const { data: user } = useUser();
  const { cafes, fetchNextPage, isFetching, hasNextPage } = useCafes();
  const { markedAsViewedCafe } = useCafeActions();
  const [activeCafe, setActiveCafe] = useState(cafes[0]);

  const shouldFetch =
    hasNextPage &&
    !isFetching &&
    cafes.findIndex((cafe) => cafe.id === activeCafe.id) + PREFETCH_OFFSET >= cafes.length;
  if (shouldFetch) {
    fetchNextPage();
  }

  return (
    <CardList>
      {cafes.map((cafe) => (
        <CafeCard
          key={cafe.id}
          cafe={cafe}
          onIntersect={(intersection: IntersectionObserverEntry) => {
            if (intersection.isIntersecting) {
              setActiveCafe(cafe);
              if (user) {
                markedAsViewedCafe({ cafeId: cafe.id });
              }
            }
          }}
        />
      ))}
    </CardList>
  );
};

export default Home;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  height: 100%;

  &::-webkit-scrollbar {
    display: none;
  }

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;
