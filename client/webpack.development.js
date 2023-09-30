import ReactRefreshPlugin from '@pmmmwh/react-refresh-webpack-plugin';
import { merge } from 'webpack-merge';
import webpackCommonConfig from './webpack.common.js';

/** @type {import('webpack').Configuration} */
export default merge(webpackCommonConfig, {
  mode: 'development',
  plugins: [new ReactRefreshPlugin()],
  devServer: {
    hot: true,
    port: 3000,
    allowedHosts: 'all',
    historyApiFallback: true,
    open: true,
  },
});
