import { styled } from 'styled-components';
import type { LikedCafe } from '../types';

type LikedCafeListProps = {
  likedCafes: LikedCafe[];
};

const LikedCafeList = (props: LikedCafeListProps) => {
  const { likedCafes } = props;

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

  &::-webkit-scrollbar {
    width: 0; /* 스크롤 바 너비 설정 */
  }
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
