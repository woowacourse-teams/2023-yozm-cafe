import { useCallback, useMemo } from 'react';

const useClipboard = () => {
  const copyToClipboard = useCallback((text: string) => {
    return window.navigator.clipboard.writeText(text);
  }, []);

  const clipboard = useMemo(() => ({ copyToClipboard }), []);
  return clipboard;
};

export default useClipboard;
