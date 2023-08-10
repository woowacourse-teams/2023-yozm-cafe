import type { InfiniteData } from '@tanstack/react-query';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import client from '../client';
import type { Cafe } from '../types';
import useAuth from './useAuth';

type SetLikedCafeParams = {
  cafeId: Cafe['id'];
  isLiked: boolean;
};

const withIsLiked = (cafes: InfiniteData<{ cafes: Cafe[] }>, cafeId: Cafe['id'], isLiked: Cafe['isLiked']) => {
  return {
    ...cafes,
    pages: cafes.pages.map((page) => ({
      ...page,
      cafes: page.cafes.map((cafe) => {
        if (cafe.id !== cafeId) return cafe;

        return {
          ...cafe,
          isLiked,
          likeCount: cafe.likeCount + (cafe.isLiked === isLiked ? 0 : isLiked ? 1 : -1),
        };
      }),
    })),
  };
};

const useCafeActions = () => {
  const queryClient = useQueryClient();
  const { identity } = useAuth();
  const { mutate: setLikedCafe } = useMutation({
    mutationFn: ({ cafeId, isLiked }: SetLikedCafeParams) => client.setLikedCafe(cafeId, isLiked),
    onMutate: async ({ cafeId, isLiked }) => {
      const cafes: InfiniteData<{ cafes: Cafe[] }> | undefined = queryClient.getQueryData(['cafes', identity]);

      queryClient.setQueryData(['cafes', identity], cafes ? withIsLiked(cafes, cafeId, isLiked) : cafes);
      return { previous: cafes };
    },
    onError: (_, __, context) => {
      queryClient.setQueryData(['cafes', identity], context?.previous);
    },
  });

  return { setLikedCafe };
};

export default useCafeActions;
