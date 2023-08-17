import { IMAGE_HOST } from '../environment';

class Image {
  static getUrl(options: { filename: string; size: 100 | 500 }) {
    const { filename, size } = options;
    return IMAGE_HOST({ size }) + filename;
  }
}

export default Image;
