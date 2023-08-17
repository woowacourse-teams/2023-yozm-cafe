import { PiHeartFill } from 'react-icons/pi';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { IMAGE_HOST } from '../environment';
import type { Rank } from '../types';

const RankCard = (props: Rank) => {
  const { id, rank, name, address, image, likeCount } = props;
  const navigate = useNavigate();

  const handleRankCardClick = () => {
    navigate(`/cafes/${id}`);
  };

  return (
    <Container onClick={handleRankCardClick}>
      <CafeRankContainer>
        <CafeRank>{rank}</CafeRank>
      </CafeRankContainer>
      <CafeDetailContainer>
        <CafeDetailSummaryContainer>
          <Image src={`${IMAGE_HOST}/100/${image}`} alt={`${image}}의 이미지`} />
          <TitleAndAddressContainer>
            <Title>{name}</Title>
            <Address>{address}</Address>
          </TitleAndAddressContainer>
        </CafeDetailSummaryContainer>
        <CafeLikeInfoContainer>
          <HeartIconContainer>
            <HeartIcon />
          </HeartIconContainer>
          <LikeCount>{likeCount}</LikeCount>
        </CafeLikeInfoContainer>
      </CafeDetailContainer>
    </Container>
  );
};

export default RankCard;

const Container = styled.article`
  display: flex;
  align-items: center;
  width: 100%;
`;

const CafeRankContainer = styled.div`
  padding: 20px;
`;

const CafeRank = styled.span`
  font-weight: bold;
`;

const Image = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50%;
`;

const CafeDetailContainer = styled.div`
  cursor: pointer;

  display: flex;
  justify-content: space-between;

  width: 100%;
  margin: ${({ theme }) => theme.space[4]} ${({ theme }) => theme.space[7]} ${({ theme }) => theme.space[4]} 0;
  padding: ${({ theme }) => theme.space['2.5']} ${({ theme }) => theme.space[5]};

  background-color: ${({ theme }) => theme.color.white};
  border-radius: 50px;
  box-shadow: 0px 5px 15px ${({ theme }) => theme.color.background.secondary};

  transition: transform 0.3s ease-in-out;

  &:hover {
    transform: scale(1.05);
  }
`;

const CafeDetailSummaryContainer = styled.div`
  display: flex;
  align-items: center;
`;

const TitleAndAddressContainer = styled.div`
  padding-left: ${({ theme }) => theme.space['2.5']};
`;

const Title = styled.h1`
  font-weight: 700;
`;

const Address = styled.span``;

const CafeLikeInfoContainer = styled.div`
  display: flex;
  align-items: center;
`;

const HeartIconContainer = styled.div``;

const HeartIcon = styled(PiHeartFill)`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.primary};
`;

const LikeCount = styled.span`
  padding-left: ${({ theme }) => theme.space[1]};
  font-size: ${({ theme }) => theme.fontSize.base};
  font-weight: 700;
`;
