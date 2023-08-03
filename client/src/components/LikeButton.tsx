import { useState } from 'react';
import { PiHeartFill } from 'react-icons/pi';
import { styled } from 'styled-components';

type LikeButtonProps = {
  likeCount: number;
  active: boolean;
  onChange: () => void;
};

const LikeButton = ({ likeCount, active, onChange }: LikeButtonProps) => {
  const [isLiked, setIsLiked] = useState(active);
  const [announce, setAnnounce] = useState('');

  const handleLikeClick = () => {
    setIsLiked((prevIsLiked) => !prevIsLiked);
    setAnnounce(isLiked ? '좋아요 취소되었습니다.' : '좋아요가 추가되었습니다.');
    setTimeout(() => setAnnounce(''), 1000);
    onChange?.();
  };

  return (
    <Container aria-label="좋아요 버튼" tabIndex={0} role="button">
      <HeartIcon $active={isLiked} onClick={handleLikeClick} />
      <LikeCount>{likeCount}</LikeCount>
      <Announcement aria-live="assertive" aria-atomic="true" aria-relevant="text">
        {`${likeCount} ${announce}`}
      </Announcement>
    </Container>
  );
};

export default LikeButton;

const HeartIcon = styled(PiHeartFill)<{ $active: boolean }>`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  color: ${({ theme, $active }) => ($active ? theme.color.primary : theme.color.white)};
`;

const LikeCount = styled.span`
  font-size: small;
  color: ${({ theme }) => theme.color.white};
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const Announcement = styled.div`
  position: absolute;
  left: -9999px;
`;
