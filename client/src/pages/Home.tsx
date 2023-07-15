import { styled } from 'styled-components';
import AppHeader from '../components/AppHeader';
import AsideActionBar from '../components/AsideActionBar';
import LikeButton from '../components/LikeButton';
import Navbar from '../components/Navbar';
import CafeInfoModal from '../components/CafeInfoModal';
import CommentButton from '../components/CommentButton';
import ShareButton from '../components/ShareButton';



const Home = () => {
  return (
    <Container>
      <AppHeader />
      <CardList></CardList>
      <Aside>
        <LikeButton currentLikeCount={1} />
        <AsideActionBar />
        <CommentButton />
        <ShareButton />
      </Aside>
      <CafeInfoModal title="성수동 카페" address="서울 성동구 연무장3길 6" content="안녕하세요안녕하세요..." />
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

const Aside = styled.div`
  position: absolute;
  right: 0;
  bottom: 250px;

  display: flex;
  flex-direction: column;
  gap: 40px;

  padding: 20px;
`;
