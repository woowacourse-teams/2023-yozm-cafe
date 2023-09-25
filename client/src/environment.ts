export const IS_DEV = process.env.NODE_ENV === 'development';

export const MSW = (process.env.MSW ?? (IS_DEV ? 'true' : 'false'))?.toLowerCase() === 'true';

export const IMAGE_HOST = ({ size }: { size: string }) =>
  MSW ? '' : IS_DEV ? `/images/${size}` : `/images/${size}`;
