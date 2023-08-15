import { useRef } from 'react';
import { styled } from 'styled-components';
import useIntersection from '../hooks/useIntersection';
import useLikedCafes from '../hooks/useLikedCafes';

const LikedCafeList = () => {
  const { likedCafes, fetchNextPage, isFetching, hasNextPage } = useLikedCafes();

  const ref = useRef<HTMLDivElement>(null);
  const intersection = useIntersection(ref, { threshold: 1 });

  const shouldFetch = hasNextPage && !isFetching && intersection?.isIntersecting;
  if (shouldFetch) fetchNextPage();

  return (
    <Container>
      <TitleContainer>
        <Title>좋아요한 카페 리스트</Title>
      </TitleContainer>
      <ScrollContainer>
        <GridContainer>
          {/* 좋아요한 카페 이미지들을 렌더링 */}
          {likedCafes.map((cafe) => (
            <CafeImage key={cafe.cafeId} src={cafe.imageUrl} alt={`Cafe ${cafe.cafeId}`} />
          ))}
        </GridContainer>
        <SensorContainer ref={ref} />
      </ScrollContainer>
    </Container>
  );
};

export default LikedCafeList;

const Container = styled.section`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const Title = styled.h1`
  margin-bottom: ${({ theme }) => theme.space['3']};
  color: ${({ theme }) => theme.color.gray};
`;

const TitleContainer = styled.article`
  display: flex;
  justify-content: center;
  margin-bottom: ${({ theme }) => theme.space['3']};
  border-bottom: solid ${({ theme }) => theme.color.line.secondary};
`;

const ScrollContainer = styled.div`
  overflow-y: scroll;
  flex: 1;
`;

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 한 줄에 3개의 열 */
  gap: ${({ theme }) => theme.space['0.5']};
  margin-bottom: ${({ theme }) => theme.space['10']};
`;

const CafeImage = styled.img`
  aspect-ratio: 1 / 1;
`;

const SensorContainer = styled.div``;
