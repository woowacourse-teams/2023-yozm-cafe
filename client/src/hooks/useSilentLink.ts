import { onlineManager } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useToast } from '../context/ToastContext';

/**
 * 온라인 혹은 오프라인 상태를 감시하고 변화가 발생할 시 토스트로 알림을 띄워주는 훅입니다.
 */
const useSilentLink = () => {
  const setToast = useToast();
  useEffect(() => {
    return onlineManager.subscribe(() => {
      const isOnline = onlineManager.isOnline();
      setToast(
        isOnline ? 'success' : 'warning',
        isOnline ? '인터넷이 다시 연결되었습니다' : '인터넷 연결이 끊어졌습니다. 확인해주세요',
      );
    });
  }, [setToast]);
};

export default useSilentLink;
