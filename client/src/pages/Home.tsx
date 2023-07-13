import { useState } from 'react';
import { styled } from 'styled-components';
import AppHeader from '../components/AppHeader';
import AsideActionBar from '../components/AsideActionBar';
import BottomNavbar from '../components/BottomNavbar';

const Home = () => {
  const [detail, setDetail] = useState(false);

  return (
    <Container>
      <AppHeader />
      <CardList></CardList>
      <Aside>
        <AsideActionBar />
      </Aside>
      <BottomSheet onClick={() => setDetail(!detail)} className={detail ? 'active' : ''}>
        <Title>성수동 카페</Title>
        <SubTitle>서울 성동구 연무장3길 6</SubTitle>

        <Content>
          성수동의 매력을 담은 카페는 도시의 소소한 아름다움과 휴식을 제공합니다. 내부는 따뜻하고 아늑한 분위기를
          자아내는 조명과 원목 가구로 장식되어 있으며, 눈부시게 아름다운 식물과 플라워 아이템이 공간을 가득 채우고
          있습니다. 그림 같은 벽면에는 감성적인 인용구와 예술적인 사진들이 걸려 있어, 이곳을 방문한 모든 손님들에게
          영감과 안락함을 선사합니다.
          <br />
          <br />
          카페는 신선하고 향긋한 원두로 만든 다양한 종류의 커피를 제공하며, 바리스타의 솜씨로 완벽하게 추출된
          에스프레소는 커피 애호가들에게 꼭 필요한 힘이 될 것입니다. 또한, 다양한 티와 음료 메뉴도 제공하여 손님들의
          다양한 취향과 선호도에 맞게 선택할 수 있습니다. 이외에도 카페는 맛있는 디저트와 베이커리도 함께 제공하여
          달콤한 휴식 시간을 즐길 수 있습니다.
          <br />
          <br />
          빵이 굽히는 향기와 함께 손님들은 고민과 스트레스를 잠시 잊고 풍성한 맛과 즐거움을 느낄 수 있습니다. 인스타
          감성 카페는 성수동의 아름다운 풍경과 함께 일상 속 작은 행복을 찾는 모든 분들을 위한 특별한 장소입니다. 여기서
          당신은 멋진 사진을 찍고, 휴식을 취하며, 소중한 시간을 보낼 수 있을 것입니다. 성수동을 방문하는 여행객이라면 꼭
          한 번 들러보세요. 카페에서 특별한 순간을 만들어보세요.
        </Content>
      </BottomSheet>
      <BottomNavbar />
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

const Content = styled.p`
  margin-top: 20px;
  color: #777777;
`;

const BottomSheet = styled.div`
  will-change: transform;

  position: absolute;
  right: 20px;
  bottom: 100px;
  left: 20px;

  display: flex;
  flex-direction: column;
  gap: 8px;

  height: 120px;
  padding: 16px;

  opacity: 0.8;
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(20px);
  border-radius: 20px;

  transition: all 0.2s;

  &:not(.active):hover {
    cursor: pointer;
    transform: scale(1.02) translateY(-4px);
  }

  &.active {
    right: 0;
    bottom: 0;
    left: 0;

    height: 700px;

    opacity: 1;
    background: #ffffff;
    backdrop-filter: none;
  }

  & > ${Content} {
    opacity: 0;
    transition: opacity 0.2s;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;

const Title = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
`;

const SubTitle = styled.h2`
  font-size: ${({ theme }) => theme.fontSize.lg};
`;

const Aside = styled.div`
  position: absolute;
  right: 0;
  bottom: 240px;

  display: flex;
  justify-content: flex-end;

  padding: 20px;
`;
