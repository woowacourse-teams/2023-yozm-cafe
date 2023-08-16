import { merge } from 'webpack-merge';
import defaultConfig from '../webpack.config.js';

export default function ({ config }) {
  return merge(defaultConfig, config);
}
