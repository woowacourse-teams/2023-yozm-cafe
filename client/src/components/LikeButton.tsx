import { useEffect, useState } from 'react';
import { PiHeartFill } from 'react-icons/pi';
import { styled } from 'styled-components';

type LikeButtonProps = {
  likeCount: number;
  active: boolean;
  onChange: () => void;
};

const LikeButton = (props: LikeButtonProps) => {
  const { likeCount, active, onChange } = props;

  const [announce, setAnnounce] = useState('');

  useEffect(() => {
    setAnnounce(active ? '좋아요 취소되었습니다.' : '좋아요가 추가되었습니다.');
    setTimeout(() => setAnnounce(''), 1000);
  }, [active]);

  const handleLikeClick = () => {
    onChange?.();
  };

  return (
    <Container data-testid="likeButton" aria-label="좋아요 버튼" tabIndex={0} role="button">
      <HeartIcon $active={active} onClick={handleLikeClick} />
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

  padding-top: ${({ theme }) => theme.space[3]};
`;

const Announcement = styled.div`
  position: absolute;
  left: -9999px;
`;
