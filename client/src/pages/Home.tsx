import { styled } from 'styled-components';
import AppHeader from '../components/AppHeader';
import AsideActionBar from '../components/AsideActionBar';
import BottomNavbar from '../components/BottomNavbar';
import CafeInfoModal from '../components/CafeInfoModal';

const Home = () => {
  return (
    <Container>
      <AppHeader />
      <CardList></CardList>
      <Aside>
        <AsideActionBar />
      </Aside>
      <CafeInfoModal title="성수동 카페" address="서울 성동구 연무장3길 6" content="안녕하세요안녕하세요..." />
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

const Aside = styled.div`
  position: absolute;
  right: 0;
  bottom: 240px;

  display: flex;
  justify-content: flex-end;

  padding: 20px;
`;
