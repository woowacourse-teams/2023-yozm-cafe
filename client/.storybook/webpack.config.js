import { merge } from 'webpack-merge';
import defaultConfig from '../webpack.development.js';

export default function ({ config }) {
  return merge(defaultConfig, config);
}
