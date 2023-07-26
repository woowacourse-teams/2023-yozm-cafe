import { styled } from 'styled-components';
import useCafeActions from '../hooks/useCafeActions';
import { Cafe } from '../types';
import CommentButton from './CommentButton';
import LikeButton from './LikeButton';
import ShareButton from './ShareButton';

type CafeActionBarProps = {
  cafe: Cafe;
};

const CafeActionBar = (props: CafeActionBarProps) => {
  const { cafe } = props;
  const { setLikedCafe } = useCafeActions();

  const handleLikeCountIncrease = () => {
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
      <Action>
        <CommentButton />
      </Action>
      <Action>
        <ShareButton />
      </Action>
    </Container>
  );
};

export default CafeActionBar;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  gap: 24px;
  align-self: flex-end;
`;

const Action = styled.button`
  color: white;
  background: transparent;
  border: none;
`;
