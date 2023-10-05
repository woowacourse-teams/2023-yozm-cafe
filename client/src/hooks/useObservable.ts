import { useSyncExternalStore } from 'react';
import type Observable from '../utils/Observable';

const useObservable = <T>(Observable: Observable<T>): [T, (state: T) => void] => {
  const state = useSyncExternalStore(
    (callback) => {
      Observable.subscribe(callback);

      return () => Observable.unsubscribe(callback);
    },
    () => Observable.getState(),
  );
  const dispatch = (state: T) => Observable.setState(state);
  return [state, dispatch];
};

export default useObservable;
