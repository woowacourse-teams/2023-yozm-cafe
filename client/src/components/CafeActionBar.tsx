import { useState } from 'react';
import { styled } from 'styled-components';
import LikeButton from './LikeButton';
import ShareButton from './ShareButton';

const CafeActionBar = () => {
  const [likeCount, setLikeCount] = useState(1);
  const [isLiked, setIsLiked] = useState(false);

  const handleLikeCountIncrease = () => {
    setIsLiked(!isLiked);
    setLikeCount(likeCount + (isLiked ? -1 : +1));
  };

  return (
    <Container>
      <Action>
        <LikeButton likeCount={likeCount} active={isLiked} onChange={handleLikeCountIncrease} />
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

  padding-right: 8px;
`;

const Action = styled.button`
  color: white;
  background: transparent;
  border: none;
`;
