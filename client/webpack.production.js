import { BundleAnalyzerPlugin } from 'webpack-bundle-analyzer';
import { merge } from 'webpack-merge';
import webpackCommonConfig from './webpack.common.js';

/** @type {import('webpack').Configuration} */
export default merge(webpackCommonConfig, {
  mode: 'production',
  output: {
    // 번들링된 파일명을 수정하는 것은 storybook 구동에 영향을 주기 때문에
    // production 빌드 시에만 사용하여야 합니다
    filename: '[name].[contenthash].js',
    publicPath: '/',
    clean: true,
  },
  plugins: [
    // new BundleAnalyzerPlugin()
  ],
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
