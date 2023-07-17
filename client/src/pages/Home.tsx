import { useMemo, useState } from 'react';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import Navbar from '../components/Navbar';
import useCafes from '../hooks/useCafes';

const PREFETCH_OFFSET = 2;

const Home = () => {
  const { isFetching, cafes, fetchNextPage } = useCafes();
  const [activeCafe, setActiveCafe] = useState(cafes[0]);

  const handleCafeChange = useMemo(() => {
    return cafes.map((cafe) => (intersection: IntersectionObserverEntry) => {
      if (intersection.isIntersecting) {
        setActiveCafe(cafe);
      }
    });
  }, [cafes]);

  if (!isFetching && cafes.findIndex((cafe) => cafe.id === activeCafe.id) + PREFETCH_OFFSET >= cafes.length) {
    fetchNextPage();
  }

  return (
    <Container>
      <CardList>
        {cafes.map((cafe, index) => (
          <CafeCard key={cafe.id} cafe={cafe} onIntersect={handleCafeChange[index]} />
        ))}
      </CardList>
      <Navbar />
    </Container>
  );
};

export default Home;

const Container = styled.main`
  position: relative;

  display: flex;
  flex-direction: column;

  width: 100%;
  height: 100%;
`;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  flex: 1;

  &::-webkit-scrollbar {
    display: none;
  }

  & > * {
    scroll-snap-align: start;
    margin-bottom: 40px;
  }
`;
