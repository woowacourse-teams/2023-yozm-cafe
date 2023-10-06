import { onlineManager } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useToast } from '../context/ToastContext';

const useSilentLink = () => {
  const setToast = useToast();
  useEffect(() => {
    return onlineManager.subscribe(() => {
      const isOnline = onlineManager.isOnline();
      setToast(isOnline ? 'success' : 'warning', isOnline ? '온라인이네요 굿' : '오프라인 헉헉');
    });
  }, [setToast]);
};

export default useSilentLink;
