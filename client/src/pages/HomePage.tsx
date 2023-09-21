import { useState } from 'react';
import { keyframes, styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useCafes from '../hooks/useCafes';
import useUser from '../hooks/useUser';

const PREFETCH_OFFSET = 2;

const HomePage = () => {
  const { data: user } = useUser();
  const { cafes, fetchNextPage, isFetching, hasNextPage } = useCafes();
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
            }
          }}
        />
      ))}
    </CardList>
  );
};

export default HomePage;

const Bounce = keyframes`
  from {
      transform: translateY(0);
  }
  to {
      transform: translateY(-10px);
  }
`;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;

  overflow-y: scroll;

  height: 100%;

  animation: ${Bounce} 0.8s ease-out;
  animation-iteration-count: 6;
  animation-direction: alternate;

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;
