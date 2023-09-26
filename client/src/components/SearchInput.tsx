import type { ComponentPropsWithoutRef } from 'react';
import styled, { css } from 'styled-components';

type SearchInputVariant = 'small' | 'large';

type SearchInputProps = ComponentPropsWithoutRef<'input'> & {
  variant?: SearchInputVariant;
};

const SearchInput = (props: SearchInputProps) => {
  const { variant = 'small', ...restProps } = props;

  return <Input $variant={variant} {...restProps} />;
};

export default SearchInput;

const Variants = {
  large: css`
    padding: ${({ theme }) => theme.space[3]};
    font-size: ${({ theme }) => theme.fontSize.lg};
  `,
  small: css`
    padding: ${({ theme }) => theme.space[2]};
    font-size: ${({ theme }) => theme.fontSize.base};
  `,
};

const Input = styled.input<{ $variant: SearchInputVariant }>`
  width: 100%;
  border: 1px solid #d0d0d0;
  border-radius: 4px;
  outline: none;

  ${({ $variant }) => Variants[$variant || 'small']}
`;
