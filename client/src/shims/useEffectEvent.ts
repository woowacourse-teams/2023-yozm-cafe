import { useCallback, useInsertionEffect, useRef } from 'react';

const useEffectEvent = <Args extends unknown[], Return>(callback: (...args: Args) => Return) => {
  const ref = useRef((..._args: Args): Return => {
    throw new Error('Cannot call an event handler while rendering.');
  });

  useInsertionEffect(() => {
    ref.current = callback;
  });

  return useCallback((...args: Args) => ref.current(...args), []);
};

export default useEffectEvent;
