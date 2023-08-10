import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useLikedCafesDetail from '../hooks/useLikedCafesDetail';

const LikedCafeDetail = () => {
  const { cafeId } = useParams();

  const { likedCafesDetail } = useLikedCafesDetail();

  useEffect(() => {
    document.getElementById(String(cafeId))?.scrollIntoView();
  }, []);

  return (
    <Container>
      {likedCafesDetail.map((cafe) => (
        <CardList key={cafe.id} id={String(cafe.id)}>
          <CafeCard cafe={cafe} />
        </CardList>
      ))}
    </Container>
  );
};

export default LikedCafeDetail;

const Container = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  height: 100%;

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
    height: 100%;
  }
`;

const CardList = styled.div``;
