package kira.progress.adapter.in.web;

import java.util.Set;

public record EndTestRequest(long questionId, Set<Long> selectedOptions) {}
