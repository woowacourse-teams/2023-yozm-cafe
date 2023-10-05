export const IS_DEV = process.env.NODE_ENV === 'development';

export const MSW = (process.env.MSW ?? (IS_DEV ? 'true' : 'false'))?.toLowerCase() === 'true';
