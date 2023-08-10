import { useMutation, useQueryClient } from '@tanstack/react-query';
import client from '../client';
import { Cafe } from '../types';
import useAuth from './useAuth';

type SetLikedCafeParams = {
  isLiked: boolean;
};

const useCafeLikes = (cafe: Cafe) => {
  const queryClient = useQueryClient();
  const { identity } = useAuth();
  const { mutate: setLiked } = useMutation({
    mutationFn: ({ isLiked }: SetLikedCafeParams) => client.setLikedCafe(cafe.id, isLiked),
    onMutate: async ({ isLiked }) => {
      const previous = queryClient.getQueryData(['cafes', cafe.id, 'likes', identity]);
      queryClient.setQueryData(['cafes', cafe.id, 'likes', identity], isLiked);
      return { previous };
    },
    onError: (_, __, context) => {
      queryClient.setQueryData(['cafes', cafe.id, 'likes', identity], context?.previous);
    },
  });

  const isLiked = queryClient.getQueryData<boolean>(['cafes', cafe.id, 'likes', identity]) ?? cafe.isLiked;

  return { isLiked, setLiked };
};

export default useCafeLikes;
