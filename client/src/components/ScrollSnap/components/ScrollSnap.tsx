import type { ScrollSnapProps } from '../types';
import ScrollSnapCSS from './ScrollSnapCSS/ScrollSnapCSS';
import ScrollSnapImpl from './ScrollSnapImpl/ScrollSnapImpl';

const isMobile = /(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i.test(window.navigator.userAgent);

const isIOS = /(iPad)|(iPhone)|(iPod)/i.test(window.navigator.userAgent);

type ScrollSnapWithMode<Item> = ScrollSnapProps<Item> & {
  // auto일 시 userAgent의 값에 따라 mode를 결정
  // css일 시 ScrollSnapCSS 사용
  // impl일 시 ScrollSnapImpl 사용
  mode?: 'auto' | 'css' | 'impl';
};

const ScrollSnap = <Item,>(props: ScrollSnapWithMode<Item>) => {
  const { mode = 'auto', ...restProps } = props;

  if (mode === 'auto') {
    if (isIOS) return <ScrollSnapCSS {...restProps} />;
    if (isMobile) return <ScrollSnapImpl {...restProps} />;

    return <ScrollSnapCSS {...restProps} />;
  }
  if (mode === 'css') {
    return <ScrollSnapCSS {...restProps} />;
  }
  return <ScrollSnapImpl {...restProps} />;
};

export default ScrollSnap;
