export const IS_DEV = process.env.NODE_ENV;

export const MSW = (process.env.MSW ?? (IS_DEV ? 'development' : 'production'))?.toLowerCase() === 'true';
