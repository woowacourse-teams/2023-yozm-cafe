import type { ScrollSnapProps } from '../types';
import ScrollSnapCSS from './ScrollSnapCSS/ScrollSnapCSS';
import ScrollSnapImpl from './ScrollSnapImpl/ScrollSnapImpl';

const isMobile = window.navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);

type ScrollSnapWithMode<Item> = ScrollSnapProps<Item> & {
  // auto일 시 userAgent의 값에 따라 mode를 결정
  // css일 시 ScrollSnapCSS 사용
  // impl일 시 ScrollSnapImpl 사용
  mode?: 'auto' | 'css' | 'impl';
};

const ScrollSnap = <Item,>(props: ScrollSnapWithMode<Item>) => {
  const { mode = 'auto', ...restProps } = props;

  if (mode === 'auto') {
    return isMobile ? <ScrollSnapImpl {...restProps} /> : <ScrollSnapCSS {...restProps} />;
  }
  if (mode === 'css') {
    return <ScrollSnapCSS {...restProps} />;
  }
  return <ScrollSnapImpl {...restProps} />;
};

export default ScrollSnap;
