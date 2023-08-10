import { Suspense, useState, type PropsWithChildren } from 'react';
import { PiReadCvLogoFill } from 'react-icons/pi';
import { styled } from 'styled-components';
import useCafeActions from '../hooks/useCafeActions';
import useUser from '../hooks/useUser';
import type { Cafe } from '../types';
import CafeMenuBottomSheet from './CafeMenuBottomSheet';
import LikeButton from './LikeButton';

type CafeActionBarProps = {
  cafe: Cafe;
};

const CafeActionBar = (props: CafeActionBarProps) => {
  const { cafe } = props;
  const { setLikedCafe } = useCafeActions();
  const { data: user } = useUser();
  const [isMenuOpened, setIsMenuOpened] = useState(false);

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

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  return (
    <Container onClick={handlePreventClickPropagation}>
      <Action>
        <LikeButton likeCount={cafe.likeCount} active={cafe.isLiked} onChange={handleLikeCountIncrease} />
      </Action>
      <Action onClick={() => setIsMenuOpened(true)}>
        <ActionButton label="메뉴">
          <PiReadCvLogoFill />
        </ActionButton>
      </Action>

      {isMenuOpened && (
        <Suspense>
          <CafeMenuBottomSheet cafe={cafe} onClose={() => setIsMenuOpened(false)} />
        </Suspense>
      )}
    </Container>
  );
};

export default CafeActionBar;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[3]};
  align-self: flex-end;

  padding-right: ${({ theme }) => theme.space[3]};
`;

const Action = styled.button`
  padding: 0;
  color: white;
  background: none;
  border: none;
`;

type ActionButtonProps = PropsWithChildren<{
  label: string;
}>;

const ActionButton = (props: ActionButtonProps) => {
  const { label, children } = props;

  return (
    <ActionButtonContainer>
      <ActionButtonIcon>{children}</ActionButtonIcon>
      {label}
    </ActionButtonContainer>
  );
};

const ActionButtonContainer = styled.button`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const ActionButtonIcon = styled.div`
  font-size: ${({ theme }) => theme.fontSize['4xl']};

  & > svg {
    display: block;
  }
`;
