import ReactFreshBabelPlugin from 'react-refresh/babel';

/** @type {import('@babel/core').TransformOptions} */
export default {
  presets: [
    [
      '@babel/preset-react',
      {
        runtime: 'automatic',
        development: process.env.NODE_ENV === 'development',
      },
    ],
    '@babel/preset-typescript',
  ],
  plugins: [process.env.NODE_ENV === 'development' && false && ReactFreshBabelPlugin].filter(Boolean),
};
