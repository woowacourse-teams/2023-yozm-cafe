import { Suspense, useState } from 'react';
import { BiSolidInfoCircle } from 'react-icons/bi';
import { FaShare } from 'react-icons/fa';
import { PiHeartFill, PiReadCvLogoFill } from 'react-icons/pi';
import { styled, useTheme } from 'styled-components';
import { useToast } from '../context/ToastContext';
import useCafeLikes from '../hooks/useCafeLikes';
import useClipboard from '../hooks/useClipboard';
import useUser from '../hooks/useUser';
import type { Cafe } from '../types';
import CafeMenuBottomSheet from './CafeMenuBottomSheet';
import IconButton from './IconButton';

type CafeActionBarProps = {
  cafe: Cafe;
};

const CafeActionBar = (props: CafeActionBarProps) => {
  const { cafe } = props;
  const theme = useTheme();
  const showToast = useToast();
  const clipboard = useClipboard();

  const { isLiked, setLiked } = useCafeLikes(cafe);
  const { data: user } = useUser();
  const [isMenuOpened, setIsMenuOpened] = useState(false);

  const likeCount = cafe.likeCount + (isLiked ? 1 : 0);

  const handleShare = async () => {
    try {
      await clipboard.copyToClipboard(`https://yozm.cafe/cafes/${cafe.id}`);
      showToast('success', 'URL이 복사되었습니다!');
    } catch (error) {
      showToast('error', `URL 복사 실패: ${error}`);
    }
  };

  const handleLikeCountIncrease = () => {
    if (!user) {
      showToast('error', '로그인이 필요합니다!');
      return;
    }
    setLiked({ isLiked: !isLiked });
  };

  const handleMenuOpen = () => {
    setIsMenuOpened(true);
  };

  const handleMenuClose = () => {
    setIsMenuOpened(false);
  };

  return (
    <Container>
      <IconButton label="공유" onClick={handleShare}>
        <FaShare />
      </IconButton>

      <IconButton label={String(likeCount)} onClick={handleLikeCountIncrease}>
        <PiHeartFill fill={isLiked ? theme.color.primary : theme.color.white} />
      </IconButton>

      <IconButton label="메뉴" onClick={handleMenuOpen}>
        <PiReadCvLogoFill />
      </IconButton>

      <IconButton label="더보기">
        <BiSolidInfoCircle />
      </IconButton>

      {isMenuOpened && (
        <Suspense>
          <CafeMenuBottomSheet cafe={cafe} onClose={handleMenuClose} />
        </Suspense>
      )}
    </Container>
  );
};

export default CafeActionBar;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[5]};
  align-self: flex-end;

  padding: ${({ theme }) => theme.space[3]};

  color: white;
`;
