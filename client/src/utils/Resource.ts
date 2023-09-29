class Resource {
  // eslint-disable-next-line no-useless-constructor, @typescript-eslint/no-empty-function
  private constructor() {}

  static getImageUrl(options: { filename: string; size: '100' | '500' | 'original' }) {
    const { filename, size } = options;
    return Resource.joinPath('/images', size, filename);
  }

  static getAssetUrl(options: { filename: string }) {
    const { filename } = options;
    return Resource.joinPath('/assets', filename);
  }

  static joinPath(...paths: string[]) {
    return paths
      .map((part, index) => {
        if (index > 0) {
          part = part.replace(/^\/+/, '');
        }
        if (index !== paths.length - 1) {
          part = part.replace(/\/+$/, '');
        }
        return part;
      })
      .join('/');
  }
}

export default Resource;
