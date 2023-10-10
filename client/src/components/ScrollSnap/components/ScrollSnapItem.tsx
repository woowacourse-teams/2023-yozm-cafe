import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

type ScrollSnapItemProps = PropsWithChildren<{
  // 이전 아이템인지, 현재 아이템인지, 이후 아이템인지 여부를 나타내는 숫자
  // -1 | 0 | 1 혹은 number
  offset: number;
  // 0.0 ~ 1.0
  position: number;
}>;

/**
 * scroll snap 컨테이너에서 snap 처리가 될 아이템
 *
 * 높이는 부모의 100%로 고정되어 있다
 */
const ScrollSnapItem = (props: ScrollSnapItemProps) => {
  const { offset, position, children } = props;

  return <Container>{children}</Container>;
};

export default ScrollSnapItem;

const Container = styled.div`
  height: 100%;
`;
