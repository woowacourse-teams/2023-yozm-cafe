import { IMAGE_HOST } from '../environment';

class Image {
  static getUrl(options: { filename: string; size: 100 | 500 }) {
    const { filename, size } = options;
    const paths = [IMAGE_HOST({ size }), filename];

    return paths
      .filter((path) => path)
      .join('/')
      .replace(/(?<!:)\/{2,}/, '/');
  }
}

export default Image;
