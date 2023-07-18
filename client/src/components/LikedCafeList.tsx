import { styled } from 'styled-components';

const LikedCafeList = () => {
  // 가상의 카페 데이터 배열
  const cafes = [
    { id: 1, image: '/images/cafe-image-1.png' },
    { id: 2, image: '/images/cafe-image-2.png' },
    { id: 3, image: '/images/cafe-image-3.png' },
    { id: 4, image: '/images/cafe-image-4.png' },
    { id: 5, image: '/images/cafe-image-5.png' },
    { id: 6, image: '/images/cafe-image-5.png' },
    { id: 7, image: '/images/cafe-image-5.png' },
    { id: 8, image: '/images/cafe-image-5.png' },
    { id: 9, image: '/images/cafe-image-5.png' },
    { id: 10, image: '/images/cafe-image-5.png' },
  ];

  return (
    <Container>
      <TitleContainer>
        <Title>좋아요한 카페 리스트</Title>
      </TitleContainer>
      <ScrollContainer>
        <GridContainer>
          {cafes.map((cafe) => (
            <CafeImage key={cafe.id} src={cafe.image} alt={`Cafe ${cafe.id}`} />
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
  color: ${({ theme }) => theme.color.text.secondary};
`;

const TitleContainer = styled.article`
  display: flex;
  justify-content: center;
  margin-bottom: ${({ theme }) => theme.space['4']};
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
  gap: ${({ theme }) => theme.space['3']};
  margin-bottom: ${({ theme }) => theme.space['10']};
`;

const CafeImage = styled.img`
  width: 145px;
  height: 200px;
  border-radius: 20px;
`;
