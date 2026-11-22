import { useCallback, useEffect, useState } from 'react';
export function useCountdown() {
  const [secondsLeft, setSecondsLeft] = useState(0);

  useEffect(function setupTimer() {
    if (secondsLeft <= 0) {
      return undefined; 
    }

    const timerId = setInterval(function tick() {
      setSecondsLeft(function subtractSecond(current) {
        return current - 1;
      });
    }, 1000);
    return function cleanup() {
      clearInterval(timerId);
    };
  }, [secondsLeft]);

  const start = useCallback(function startTimer(seconds) {
    setSecondsLeft(seconds);
  }, []);

  const stop = useCallback(function stopTimer() {
    setSecondsLeft(0);
  }, []);

  return { secondsLeft, start, stop };
}
export function formatCountdown(totalSeconds) {
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;
  const paddedSeconds = seconds < 10 ? `0${seconds}` : `${seconds}`;
  return `${minutes}:${paddedSeconds}`;
}