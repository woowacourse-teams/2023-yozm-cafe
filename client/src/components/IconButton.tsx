import type { ComponentPropsWithoutRef } from 'react';
import styled from 'styled-components';

type IconButtonProps = ComponentPropsWithoutRef<'button'> & {
  label?: string;
};

const IconButton = (props: IconButtonProps) => {
  const { label, children, ...restProps } = props;

  return (
    <Container aria-label={label} {...restProps}>
      <Icon>{children}</Icon>

      {label && <Label>{label}</Label>}
    </Container>
  );
};

export default IconButton;

const Container = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Icon = styled.div`
  font-size: ${({ theme }) => theme.fontSize['4xl']};

  & > * {
    display: block;
  }
`;

const Label = styled.span``;
