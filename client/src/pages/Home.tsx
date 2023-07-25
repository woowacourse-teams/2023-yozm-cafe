import { useMemo, useState } from 'react';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useCafes from '../hooks/useCafes';

const PREFETCH_OFFSET = 2;

const Home = () => {
  const { cafes, fetchNextPage, isFetching, hasNextPage } = useCafes();
  const [activeCafe, setActiveCafe] = useState(cafes[0]);

  // https://github.com/woowacourse-teams/2023-yozm-cafe/pull/49#discussion_r1264872201
  //
  // 아래의 `<CafeCard />` 컴포넌트에 `onIntersect` prop으로 콜백 함수를 넣어주어야 합니다.
  // 참조 동일성을 지켜주지 않으면 렌더링 무한 루프가 발생하기 때문에,
  // useCallback 혹은 useMemo를 사용해야 합니다.
  //
  // 다만 콜백 함수를 배열로 만들어 주어야 하기 때문에 useCallback을 사용하기 어렵다고 판단했고,
  // useMemo를 사용하였습니다.
  const handleCafeChange = useMemo(() => {
    return cafes.map((cafe) => (intersection: IntersectionObserverEntry) => {
      if (intersection.isIntersecting) {
        setActiveCafe(cafe);
      }
    });
  }, [cafes]);

  const shouldFetch =
    hasNextPage && isFetching && cafes.findIndex((cafe) => cafe.id === activeCafe.id) + PREFETCH_OFFSET >= cafes.length;
  if (shouldFetch) {
    fetchNextPage();
  }

  return (
    <CardList>
      {cafes.map((cafe, index) => (
        <CafeCard key={cafe.id} cafe={cafe} onIntersect={handleCafeChange[index]} />
      ))}
    </CardList>
  );
};

export default Home;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  height: 100%;

  &::-webkit-scrollbar {
    display: none;
  }

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;
