import { useState } from 'react';
import { SlArrowDown } from 'react-icons/sl';
import { styled } from 'styled-components';

type CafeInfoModalProps = {
  title: string;
  address: string;
  content: string;
};

const CafeInfoModal = ({ title, address, content }: CafeInfoModalProps) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <Container className={isOpen ? 'active' : ''} onClick={() => setIsOpen(!isOpen)}>
        <Title>{title}</Title>
        <Address>{address}</Address>
        {!isOpen && (
          <ArrowDownContainer>
            <StyledArrowDown />
          </ArrowDownContainer>
        )}
        <Content>{content}</Content>
      </Container>
    </>
  );
};

export default CafeInfoModal;

const StyledArrowDown = styled(SlArrowDown)`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  color: ${({ theme }) => theme.color.line.secondary};
`;

const ArrowDownContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Title = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
`;

const Address = styled.h2`
  font-size: ${({ theme }) => theme.fontSize.lg};
`;

const Content = styled.p`
  margin-top: 20px;
  color: ${({ theme }) => theme.color.text.secondary};
`;

const Container = styled.main`
  will-change: transform;

  position: absolute;
  right: 20px;
  bottom: 100px;
  left: 20px;

  display: flex;
  flex-direction: column;
  gap: 8px;

  height: 120px;
  padding: 16px;

  opacity: 0.8;
  background: ${({ theme }) => theme.color.background.primary};
  backdrop-filter: blur(20px);
  border-radius: 20px;

  transition: all 0.2s;

  &:not(.active):hover {
    cursor: pointer;
    transform: scale(1.02) translateY(-4px);
  }

  &.active {
    right: 0;
    bottom: 0;
    left: 0;

    height: 700px;

    opacity: 1;
    background: ${({ theme }) => theme.color.white};
    backdrop-filter: none;
  }

  & > ${Content} {
    opacity: 0;
    transition: opacity 0.2s;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;
