import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useLikedCafesDetail from '../hooks/useLikedCafesDetail';

const LikedCafeDetailPage = () => {
  const params = useParams();
  const cafeId = Number(params.cafeId);

  const { likedCafesDetail } = useLikedCafesDetail();
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    ref.current?.scrollIntoView();
  }, []);

  return (
    <CardList>
      {likedCafesDetail.map((cafe) => (
        <CafeCardContainer key={cafe.id} ref={cafe.id === cafeId ? ref : undefined}>
          <CafeCard cafe={cafe} />
        </CafeCardContainer>
      ))}
    </CardList>
  );
};

export default LikedCafeDetailPage;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  height: 100%;

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
    height: 100%;
  }
`;

const CafeCardContainer = styled.div``;
