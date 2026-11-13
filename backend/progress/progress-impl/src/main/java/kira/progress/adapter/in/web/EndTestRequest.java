package kira.progress.adapter.in.web;

import java.util.Set;

record EndTestRequest(long questionId, Set<Long> selectedOptions) {}
