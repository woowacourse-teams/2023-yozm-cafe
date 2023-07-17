import { useState } from 'react';
import { styled } from 'styled-components';
import CommentButton from './CommentButton';
import LikeButton from './LikeButton';
import ShareButton from './ShareButton';

const CafeActionBar = () => {
  const [likeCount, setLikeCount] = useState(1);
  const [isLiked, setIsLiked] = useState(false);

  const handleLikeChange = () => {
    if (isLiked) {
      setLikeCount(likeCount - 1);
    } else {
      setLikeCount(likeCount + 1);
    }
    setIsLiked(!isLiked);
  };

  return (
    <Container>
      <Action>
        <LikeButton likeCount={1} onChange={handleLikeChange} />
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
