import { BundleAnalyzerPlugin } from 'webpack-bundle-analyzer';
import { merge } from 'webpack-merge';
import webpackCommonConfig from './webpack.common.js';

/** @type {import('webpack').Configuration} */
export default merge(webpackCommonConfig, {
  mode: 'production',
  plugins: [new BundleAnalyzerPlugin()],
  optimization: {
    usedExports: true,
    minimize: true,
    // minimizer: [],
    sideEffects: false,
    splitChunks: {
      chunks: 'all',
    },
  },
  externals: {
    msw: 'msw',
  },
});
