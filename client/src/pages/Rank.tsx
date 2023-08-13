import { useRef } from 'react';
import { styled } from 'styled-components';
import RankCard from '../components/RankCard';
import useIntersection from '../hooks/useIntersection';
import useRank from '../hooks/useRank';

const Rank = () => {
  const { rankedCafes, fetchNextPage, isFetching, hasNextPage } = useRank();

  const ref = useRef<HTMLDivElement>(null);
  const intersection = useIntersection(ref, { threshold: 1 });

  const shouldFetch = hasNextPage && !isFetching && intersection?.isIntersecting;

  if (shouldFetch) fetchNextPage();

  return (
    <Container>
      <TitleAndCategoryContainer>
        <Title>랭킹</Title>
        <Category>좋아요 순</Category>
      </TitleAndCategoryContainer>
      {rankedCafes.map((cafe) => (
        <RankCard
          key={cafe.id}
          id={cafe.id}
          name={cafe.name}
          address={cafe.address}
          image={cafe.image}
          rank={cafe.rank}
          likeCount={cafe.likeCount}
        />
      ))}
      <SensorContainer ref={ref}>sss</SensorContainer>
    </Container>
  );
};

export default Rank;

const Container = styled.section`
  padding-bottom: ${({ theme }) => theme.space[4]};
`;

const TitleAndCategoryContainer = styled.div`
  width: 100%;
  margin-bottom: ${({ theme }) => theme.space[4]};
  padding: 0 0 ${({ theme }) => theme.space[3]} ${({ theme }) => theme.space[6]};
  border-bottom: solid ${({ theme }) => theme.color.line.secondary} 0.3px;
`;

const Title = styled.h1`
  padding-bottom: ${({ theme }) => theme.space[4]};
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  font-weight: 600;
`;

const Category = styled.span`
  padding-bottom: ${({ theme }) => theme.space['2.5']};
  font-weight: 600;
  border-bottom: solid ${({ theme }) => theme.color.black} 2.5px;
`;

const SensorContainer = styled.div`
  width: 100%;
  background-color: red;
`;
