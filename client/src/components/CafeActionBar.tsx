import { styled } from 'styled-components';
import useCafeActions from '../hooks/useCafeActions';
import useUser from '../hooks/useUser';
import { Cafe } from '../types';
import LikeButton from './LikeButton';

type CafeActionBarProps = {
  cafe: Cafe;
};

const CafeActionBar = (props: CafeActionBarProps) => {
  const { cafe } = props;
  const { setLikedCafe } = useCafeActions();
  const { data: user } = useUser();

  const handleLikeCountIncrease = () => {
    if (!user) {
      alert('로그인이 필요합니다!');
      return;
    }

    setLikedCafe({
      cafeId: cafe.id,
      isLiked: !cafe.isLiked,
    });
  };

  return (
    <Container>
      <Action>
        <LikeButton likeCount={cafe.likeCount} active={cafe.isLiked} onChange={handleLikeCountIncrease} />
      </Action>
    </Container>
  );
};

export default CafeActionBar;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  align-self: flex-end;
  padding-right: ${({ theme }) => theme.space[3]};
`;

const Action = styled.button`
  padding: 0;
  color: white;
  background: none;
  border: none;
`;
