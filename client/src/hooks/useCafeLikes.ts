import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react'; // useState 가져오기
import client from '../client';
import { type Cafe } from '../types';
import useAuth from './useAuth';

type SetLikedCafeParams = {
  isLiked: boolean;
};

const useCafeLikes = (cafe: Cafe) => {
  const queryClient = useQueryClient();
  const { identity } = useAuth();
  const [isLiked, internalSetIsLiked] = useState(cafe.isLiked);
  const { mutate: setLiked } = useMutation({
    mutationFn: ({ isLiked }: SetLikedCafeParams) => client.setLikedCafe(cafe.id, isLiked),
    onMutate: async ({ isLiked }) => {
      const previous = isLiked;
      internalSetIsLiked(isLiked);
      return { previous };
    },
    onError: (_, __, context) => {
      internalSetIsLiked(context?.previous ?? cafe.isLiked);
      queryClient.invalidateQueries(['cafes', identity], undefined);
      queryClient.invalidateQueries(['likedCafeDetail', identity], undefined);
    },
  });

  return { isLiked, setLiked };
};

export default useCafeLikes;
